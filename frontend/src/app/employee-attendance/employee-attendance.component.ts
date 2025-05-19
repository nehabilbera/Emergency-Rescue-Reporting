import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { AttendanceService } from '../services/attendance.service';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-employee-attendance',
  standalone: true,
  imports: [NgFor, FormsModule, NgIf],
  templateUrl: './employee-attendance.component.html',
  styleUrls: ['./employee-attendance.component.css']
})
export class EmployeeAttendanceComponent implements OnInit {

  employees: any[] = [];
  employeeRecords: any[] = [];

  showDisplay: boolean = false;
  errorMessage: string = '';
  message: string = '';

  newAttendance = {
    employeeId: '',
    name: '',
    date: '',
    checkIn: '',
    checkOut: ''
  };

  constructor(private attendanceService: AttendanceService, private cdr: ChangeDetectorRef) {}

  ngOnInit() {}

  submitAttendance() {
    // console.log(this.newAttendance.employeeId)
    this.startCounter();
    this.showDisplay = true;
    this.attendanceService.getEmployeeHistory(this.newAttendance.employeeId).subscribe(
      (data: any) => {
        this.employees = data;
      }
    );
  }

    
  
  onToggleCheckInOut() {
    const employeeId = this.newAttendance.employeeId;
    console.log("HI : ", employeeId);
    this.attendanceService.toggleCheckInOut(employeeId).subscribe({
      next: (response) => {
        console.log("Ok", response);
        alert(response)
      },
      error: (error) => {
        console.error('Error',error);
      }
    });
  }
  

  showCounter: boolean = false;
  counter: number = 0;
  
  startCounter() {
    this.showCounter = true;
    this.counter = 0;
    const interval = setInterval(() => {
      this.counter++;
      this.cdr.detectChanges(); // Trigger change detection
      if (this.counter >= 5) {
        clearInterval(interval);
        setTimeout(() => {
          this.showCounter = false;
          this.cdr.detectChanges(); // Trigger change detection
        }, 500);
      }
    }, 500);
  }

}
