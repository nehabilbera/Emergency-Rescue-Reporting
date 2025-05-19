import mysql.connector
from config import DB_CONFIG

def get_connection():
    return mysql.connector.connect(**DB_CONFIG)

def setup_tables():
    conn = get_connection()
    cursor = conn.cursor()

    cursor.execute('''
        CREATE TABLE IF NOT EXISTS trained_pictures (
            id INT AUTO_INCREMENT PRIMARY KEY,
            employeeId INT UNIQUE NOT NULL,
            image_path TEXT NOT NULL
        )
    ''')

    cursor.execute('''
        CREATE TABLE IF NOT EXISTS face_attendance (
            id INT PRIMARY KEY,
            employeeId INT NOT NULL,
            date DATE NOT NULL,
            time TIME NOT NULL
        )
    ''')
    print("db created")

    conn.commit()
    conn.close()
