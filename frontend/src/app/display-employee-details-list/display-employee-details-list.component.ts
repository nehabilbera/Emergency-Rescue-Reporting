import { Component, OnInit } from '@angular/core';
import { EmployeesService } from '../services/employees.service';
import { NgClass, NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-display-employee-details-list',
  imports: [NgFor, NgClass, NgIf],
  templateUrl: './display-employee-details-list.component.html',
  styleUrls: ['./display-employee-details-list.component.css']
})
export class DisplayEmployeeDetailsListComponent implements OnInit {

  employees: any[] = [];

  constructor(private employeesService: EmployeesService) { }

  ngOnInit() {
    this.employeesService.getEmployeeList().subscribe((data: any) => {
      // console.log(data);
      this.employees = data;
      this.checkAllEmployees();
    });
  }

  checkAllEmployees() {
    this.employees.forEach(emp => {
      this.checkEmployee(emp.employeeId);
    });
  }

  checkEmployee(employeeId: any) {
    // console.log(employeeId)
    this.employeesService.checkEmployee(employeeId).subscribe(response => {
      if (response.status === 'pending') {
        this.updateEmployeeStatus(employeeId, 'Pending');
        // this.clickEmployeePhoto(employeeId);
      } else if (response.status === 'ok') {
        this.updateEmployeeStatus(employeeId, 'Success');
      }
    });
  }

  clickEmployeePhoto(employeeId: any) {
    this.collectImages(employeeId);
  }

  collectImages(employeeId: any) {
    this.employeesService.collectImages(employeeId).subscribe(response => {
      if (response.status === 'ok') {
        this.updateEmployeeStatus(employeeId, 'Success');
      }
      console.log(response);
    });
  }

  updateEmployeeStatus(employeeId: any, status: string) {
    const employee = this.employees.find(emp => emp.employeeId === employeeId);
    if (employee) {
      employee.biometric = status;
    }
  }
}
