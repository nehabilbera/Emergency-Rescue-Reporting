import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeTodayAttendanceListComponent } from './employee-today-attendance-list.component';

describe('EmployeeTodayAttendanceListComponent', () => {
  let component: EmployeeTodayAttendanceListComponent;
  let fixture: ComponentFixture<EmployeeTodayAttendanceListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeeTodayAttendanceListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmployeeTodayAttendanceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
