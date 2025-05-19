import { Component } from '@angular/core';
import { LoginComponent } from '../login/login.component';
import { EmployeeAttendanceComponent } from '../employee-attendance/employee-attendance.component';

@Component({
  selector: 'app-home',
  imports: [LoginComponent,EmployeeAttendanceComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
