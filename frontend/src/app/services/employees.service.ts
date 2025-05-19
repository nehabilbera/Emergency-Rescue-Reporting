import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class EmployeesService {

  constructor(private http:HttpClient) { }

  getEmployeeList(){
    const url="http://localhost:2800/employees";
    return this.http.get(url)
  }

  saveEmployee(employee:any):Observable<any>{
    const url="http://localhost:2800/employees";
  return this.http.post<any>(url,employee)
  }

  private baseUrl = 'http://127.0.0.1:5000';
  
  checkEmployee(employeeId: any): Observable<any> {
    return this.http.get(`${this.baseUrl}/check_employee/${employeeId}`);
  }
   

  collectImages(employeeId: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/collect_images/${employeeId}`, {});
  }
}

