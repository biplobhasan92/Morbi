import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/_services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit{

  form: any = {};
  isSuccessful = false;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(){
    this.form = this.authService.signUpData;
  }



  signUp(){
    this.authService.register(this.form).subscribe(
      res => this.router.navigate(['regSuccessCompo']),
      err => console.log(console.log(err.error.message), alert(''+err.error.message))
    );
  }

}
