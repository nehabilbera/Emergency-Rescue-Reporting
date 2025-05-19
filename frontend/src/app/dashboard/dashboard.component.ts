import { Component, OnInit  } from '@angular/core';
import { RouterLink, RouterOutlet, NavigationEnd, Router } from '@angular/router'; 

@Component({
  selector: 'app-dashboard',
  imports: [RouterOutlet, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})

export class DashboardComponent {
   
}

