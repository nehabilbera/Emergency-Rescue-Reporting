from flask import Flask, request, jsonify
from flask_cors import CORS
import os
import cv2
import time
import numpy as np
from datetime import datetime
from config import DB_CONFIG, MODEL_PATH, DATASET_PATH
from db import get_connection, setup_tables
from collect_images import collect_images
from train_model import train_model
from recognize import recognize

app = Flask(__name__)
CORS(app)

@app.route('/check_employee/<employeeId>', methods=['GET'])
def check_employee(employeeId):
    conn = get_connection()
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM trained_pictures WHERE employeeId=%s", (employeeId,))
    if cursor.fetchone():
        return jsonify({"status": "ok"})
    else:
        return jsonify({"status": "pending"})

@app.route('/collect_images/<employeeId>', methods=['POST'])
def collect_images_api(employeeId):
    collect_images(employeeId)
    return jsonify({"status": "ok"})


@app.route('/recognize', methods=['GET'])
def recognize_faces():
    label_map = train_model()
    recognized_ids = recognize(label_map)
    return jsonify({"status": "ok", "recognized_ids": recognized_ids})


if __name__ == '__main__':
    setup_tables()
    app.run(debug=True)
