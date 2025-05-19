from db import setup_tables
from collect_images import collect_images
from train_model import train_model
from recognize import recognize

def main():
    setup_tables()

    print("\n--- Face Recognition System ---")
    print("1. Register new person (Collect images + Train model)")
    print("2. Recognize and log attendance")
    
    choice = input("Enter your choice (1 or 2): ")

    if choice == '1':
        employeeId = input("Enter employee Id to register: ").strip()
        if employeeId:
            collect_images(employeeId)
            label_map = train_model()
            print(f"\nModel trained for {employeeId}.")
        else:
            print("Name cannot be empty.")

    elif choice == '2':
        label_map = train_model()  # Load trained labels for recognition
        recognize(label_map)

    else:
        print("Invalid choice. Please enter 1 or 2.")

if __name__ == '__main__':
    main()
