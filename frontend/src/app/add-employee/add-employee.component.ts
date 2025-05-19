import { Component, OnInit } from '@angular/core';
import { EmployeesService } from '../services/employees.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { NgForOf, NgIf } from '@angular/common';

@Component({
  selector: 'app-add-employee',
  imports: [FormsModule],
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent implements OnInit {
  employee = {
    name: '',
    phone_number: '',
    alternate_phone_number: '',
    medical_issue: '',
    dob: '',
    address: '',
    blood_group: ''
  };

  // Array to hold employee details
  employees: any[] = [];

  submitValue = false;

  constructor(private employeesService: EmployeesService) { }

  ngOnInit() {}

  addEmployee(employee: any) {
    const newEmployee = { ...employee };
    this.employees.push(newEmployee);

    this.employeesService.saveEmployee(newEmployee).subscribe((response: any) => {
      console.log('Saved:', response);
    });

    alert('Added Employee Successfully...!');
    console.log(newEmployee);
    this.submitValue = !this.submitValue;
    this.resetForm();
  }

  resetForm() {
    this.employee = {
      name: '',
      phone_number: '',
      alternate_phone_number: '',
      medical_issue: '',
      dob: '',
      address: '',
      blood_group: ''
    };
  }
}
