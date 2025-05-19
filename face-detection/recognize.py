from datetime import datetime, timedelta
import cv2
from db import get_connection
from config import MODEL_PATH

def recognize(label_map, cam_id=0):
    recognizer = cv2.face.LBPHFaceRecognizer_create()
    recognizer.read(MODEL_PATH)
    reverse_map = {v: k for k, v in label_map.items()}
    conn = get_connection()
    cursor = conn.cursor()

    cam = cv2.VideoCapture(cam_id)
    detector = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

    recognized_ids = set()  # To store recognized employee IDs
    start_time = datetime.now()

    while (datetime.now() - start_time).seconds < 5:
        ret, frame = cam.read()
        if not ret:
            break

        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        faces = detector.detectMultiScale(gray, 1.3, 5)

        for (x, y, w, h) in faces:
            face_img = gray[y:y+h, x:x+w]
            id_, confidence = recognizer.predict(face_img)

            if confidence < 70:
                employeeId = reverse_map[id_]
                recognized_ids.add(employeeId)  # Add recognized ID to the set
                now = datetime.now()
                date, time = now.date(), now.time()

                # Check if user already has a record in the last hour
                cursor.execute('''
                    SELECT time FROM face_attendance 
                    WHERE employeeId=%s AND date=%s 
                    ORDER BY time DESC LIMIT 1
                ''', (employeeId, date))
                last_entry = cursor.fetchone()

                should_insert = True
                if last_entry:
                    last_time = datetime.strptime(str(last_entry[0]), '%H:%M:%S')
                    if (datetime.combine(date, time) - datetime.combine(date, last_time.time())).total_seconds() < 3600:
                        should_insert = False  # Don't insert if within last hour
                    else:
                        # Remove old entry
                        cursor.execute('DELETE FROM face_attendance WHERE employeeId = %s AND date = %s', (employeeId, date))
                        conn.commit()

                if should_insert:
                    some_id = 101
                    cursor.execute('INSERT INTO face_attendance (id, employeeId, date, time) VALUES (%s, %s, %s, %s)', (some_id, employeeId, date, time))
                    conn.commit()

                    # Reindex ID to be 1, 2, 3,...
                    cursor.execute('SET @count = 0')
                    cursor.execute('UPDATE face_attendance SET id = @count := @count + 1')
                    conn.commit()

                # Draw the recognized face
                cv2.putText(frame, str(employeeId), (x, y-10), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0,255,0), 2)
                cv2.rectangle(frame, (x, y), (x+w, y+h), (0,255,0), 2)
            else:
                cv2.putText(frame, "Unknown", (x, y-10), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0,0,255), 2)
                cv2.rectangle(frame, (x, y), (x+w, y+h), (0,0,255), 2)

        cv2.imshow("Recognition", frame)
        if cv2.waitKey(1) == 27:  # ESC to exit
            break

    cam.release()
    conn.close()
    cv2.destroyAllWindows()

    return list(recognized_ids)  # Return the list of recognized employee IDs


