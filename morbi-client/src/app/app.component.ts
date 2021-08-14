import { Component, OnInit } from '@angular/core';
import {Title, Meta} from '@angular/platform-browser';
import { TokenStorageService } from './_services/token-storage.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit{
  title = 'morbi-client';
  private roles: string[];
  isLoggedIn = false;
  showAdminBoard = false;
  showUserBoard = false;
  username: string;

  constructor(private titleService: Title, private meta: Meta, private tokenStorageService: TokenStorageService,  private router: Router){
    titleService.setTitle('Morbi | Home');

    meta.addTag({name: 'x-ua-compatible', content:'ie=edge'}, true);
    meta.addTag({name: 'description', content:'Morbi Application'});
    meta.addTag({name: 'viewport', content:'width=device-width, initial-scale=1'}, true);
  }

  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showUserBoard = this.roles.includes('ROLE_USER');
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      

      this.username = user.username;
    }
  }
}
