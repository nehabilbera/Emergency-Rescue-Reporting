import os
import cv2
import numpy as np
from db import get_connection
from config import MODEL_PATH

def train_model():
    recognizer = cv2.face.LBPHFaceRecognizer_create()
    conn = get_connection()
    cursor = conn.cursor()
    cursor.execute("SELECT employeeId, image_path FROM trained_pictures")
    rows = cursor.fetchall()
    conn.close()

    faces, ids, label_map = [], [], {}
    current_id = 0

    for employeeId, folder_path in rows:
        if employeeId not in label_map:
            label_map[employeeId] = current_id
            current_id += 1

        for img_name in os.listdir(folder_path):
            img_path = os.path.join(folder_path, img_name)
            img = cv2.imread(img_path, cv2.IMREAD_GRAYSCALE)
            if img is not None:
                faces.append(img)
                ids.append(label_map[employeeId])

    recognizer.train(faces, np.array(ids))
    os.makedirs(os.path.dirname(MODEL_PATH), exist_ok=True)
    recognizer.save(MODEL_PATH)
    print("[INFO] Model trained and saved.")
    return label_map
