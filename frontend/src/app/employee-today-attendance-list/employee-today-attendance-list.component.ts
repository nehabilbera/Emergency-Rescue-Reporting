import { Component, OnInit } from '@angular/core';
import { AttendanceService } from '../services/attendance.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-employee-today-attendance-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee-today-attendance-list.component.html',
  styleUrls: ['./employee-today-attendance-list.component.css']
})

export class EmployeeTodayAttendanceListComponent implements OnInit {
  employees: any[] = [];            
  selectedDateData: any[] = [];    
  selectedDate: string = ''; 
  employeeIds: any[] = [];

  constructor(private attendanceService: AttendanceService) {}

  ngOnInit() {
    // Set the default selected date to today
    this.selectedDate = new Date().toISOString().split('T')[0];
    this.loadEmployeeData();
  }
  

  onDateChange() {
    console.log("Date changed:", this.selectedDate);
    this.loadEmployeeData();  
  }

  // Method to load the employee data
  loadEmployeeData() {
    this.attendanceService.getTodayEmployeeHistory(this.selectedDate).subscribe((data: any[]) => {
      this.employees = data;
      // console.log(data);
      this.filterByDate();
      // this.recognizeData();
    });
  }


  filterByDate() {
    this.selectedDateData = this.employees.filter(emp => emp.date === this.selectedDate
    );
  }

  
isSafe(employeeId: any): boolean {
   return this.employeeIds.includes(employeeId);
   }
  
emer = false 

emergency(){
  alert("Emergency button clicked!");
  this.attendanceService.getRecognizedEmployees().subscribe((data :any)=> {
    this.employeeIds = data.recognized_ids;
    console.log(this.employeeIds);
  },
  error => {
    console.error('Error fetching recognized employees', error);
  }
);
this.emer = true;
}

}

