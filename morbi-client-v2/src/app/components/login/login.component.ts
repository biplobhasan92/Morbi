import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/_services/auth.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()){
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;      
    }
  }


  onSubmit(){
    this.authService.login(this.form).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
        this.reloadPage();
        sessionStorage.setItem('loggedUser', data.username);
        this.router.navigate(['profile']);        
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;        
      }
    );
  }
  
  eheckSession(sessionName){
    console.log(" #### session: "+sessionName);
  }

  reloadPage() {
    window.location.reload();
  }


  passwordShow: boolean = false;
  passwordType: string = 'password';
  public clickEvent(){
    if(this.passwordShow){
      this.passwordShow = false;
      this.passwordType = 'password';
    }else{
      this.passwordShow = true;
      this.passwordType = 'text';
    }
  }

  
}
