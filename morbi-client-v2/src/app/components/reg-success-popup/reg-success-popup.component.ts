import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-reg-success-popup',
  templateUrl: './reg-success-popup.component.html',
  styleUrls: ['./reg-success-popup.component.css']
})
export class RegSuccessPopupComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  gotoLogin() {
    this.router.navigate(['login'])
  }
}
