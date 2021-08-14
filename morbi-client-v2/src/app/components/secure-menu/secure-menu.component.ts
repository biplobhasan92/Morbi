import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-secure-menu',
  templateUrl: './secure-menu.component.html',
  styleUrls: ['./secure-menu.component.css']
})
export class SecureMenuComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void{
  }

  logout(){
    // this.tokenStorageService.signOut();
    this.router.navigate(['login']);
    sessionStorage.clear();
  }
  
  gotoProfileEdit(){
    this.router.navigate(['profileEdit']);
  }


  gotoProfile(){
    this.router.navigate(['profile']);
  }

  
  gotoVideo(){
    this.router.navigate(['videoLibrary']);
  }


  gotoIndex(){
    this.router.navigate(['InboxComponent']);
  }

}
