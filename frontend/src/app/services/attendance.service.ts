
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class AttendanceService {

  constructor(private http:HttpClient) { }

  private baseUrl = 'http://localhost:2800/history';

  getEmployeeHistory(id: any): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${id}`);
  }

  private urlDate = 'http://localhost:2800/present?date';

  getTodayEmployeeHistory(id: any): Observable<any[]> {
    return this.http.get<any[]>(`${this.urlDate}=${id}`);
  }
  
toggleCheckInOut(employeeId: any): Observable<any> {
  const url = `http://localhost:2800/records/${employeeId}`;
  return this.http.post(url, {}, {responseType: 'text' as 'json'});
}
  
private apiUrl = 'http://localhost:5000/recognize'; 

getRecognizedEmployees(): Observable<any> {
   return this.http.get<any>(this.apiUrl);
   }
  
}
