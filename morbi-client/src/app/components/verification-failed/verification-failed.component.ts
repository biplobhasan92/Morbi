import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-verification-failed',
  templateUrl: './verification-failed.component.html',
  styleUrls: ['./verification-failed.component.css']
})
export class VerificationFailedComponent implements OnInit{

  constructor(private router: Router) { }

  ngOnInit(): void{
  }

  gotoLogin(){
    this.router.navigate(['login'])
  }
}
