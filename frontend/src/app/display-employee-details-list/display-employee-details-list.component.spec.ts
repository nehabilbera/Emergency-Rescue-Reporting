import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayEmployeeDetailsListComponent } from './display-employee-details-list.component';

describe('DisplayEmployeeDetailsListComponent', () => {
  let component: DisplayEmployeeDetailsListComponent;
  let fixture: ComponentFixture<DisplayEmployeeDetailsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DisplayEmployeeDetailsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisplayEmployeeDetailsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
