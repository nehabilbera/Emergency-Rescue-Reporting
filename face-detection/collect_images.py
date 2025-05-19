# collect_images.py
import time
import cv2
import os
from db import get_connection
from config import DATASET_PATH

def collect_images(employeeId, cam_id=0):
    path = os.path.join(DATASET_PATH, employeeId)
    os.makedirs(path, exist_ok=True)

    conn = get_connection()
    cursor = conn.cursor()

    cursor.execute("SELECT * FROM trained_pictures WHERE employeeId=%s", (employeeId,))
    if cursor.fetchone():
        print("[INFO] Person already exists.")
        return

    cam = cv2.VideoCapture(cam_id)
    detector = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')
    count = 0

    while count < 50:
        ret, frame = cam.read()
        if not ret:
            break
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        faces = detector.detectMultiScale(gray, 1.3, 5)

        for (x, y, w, h) in faces:
            count += 1
            face_img = gray[y:y+h, x:x+w]
            img_path = os.path.join(path, f"{count}.jpg")
            cv2.imwrite(img_path, face_img)
            time.sleep(0.1)
            cv2.rectangle(frame, (x, y), (x+w, y+h), (255,0,0), 2)

        cv2.imshow("Collecting Images", frame)
        if cv2.waitKey(1) == 27 or count >= 50:
            break

    cursor.execute("INSERT INTO trained_pictures (employeeId, image_path) VALUES (%s, %s)", (employeeId, path))
    conn.commit()
    conn.close()
    cam.release()
    cv2.destroyAllWindows()
    print(f"[INFO] Collected {count} images for {employeeId}.")
