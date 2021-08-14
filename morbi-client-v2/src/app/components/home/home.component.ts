import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../../_services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  signUpData: any = {};

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.signUpData = this.signUpData;
  }

  saveSignUpData() {
    this.router.navigate(['registration']);
  }
}
