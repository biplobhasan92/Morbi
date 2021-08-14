import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../_services/token-storage.service';
import { Router } from '@angular/router';
import { Observable } from "rxjs";
import { UserService } from '../../_services/user.service';
import { AuthService } from 'src/app/_services/auth.service';
import { Inbox } from './inbox';
import { UserCombo } from './usercombo';
import { User } from '../user';


@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css', '../profile/profile.component.css']
})

export class InboxComponent implements OnInit{

  form: any = {};
  inboxs: Observable<Inbox[]>;
  users: Observable<User[]>;
  errorMessage='';
  constructor(private authService: AuthService, private tokenStorageService: TokenStorageService, private router: Router,private userservice: UserService) { }
  userDisplay ='';
  userid : any;

  ngOnInit(): void {
    this.userDisplay = sessionStorage.getItem('loggedUser');
    this.eheckSession(this.userDisplay);
    this.userid = sessionStorage.getItem('userid'); //work from here ... 
    this.reloadData();
  }


  logout() {
    this.tokenStorageService.signOut();
    this.router.navigate(['login']);
    sessionStorage.clear();
  }

  eheckSession(sessionName){
    if(sessionName=='' || sessionName==null){
      this.router.navigate(['login']); 
    }else{
      
    }
  }

  gotoProfileEdit(){
    this.router.navigate(['profileEdit']);
  }

  gotoVideo(){
    this.router.navigate(['videoLibrary']);
  }

  gotoProfile(){
    this.router.navigate(['profile']);
  }
  
  gotoIndex(){
    this.router.navigate(['InboxComponent']);
  }

  reloadData(){
    this.inboxs = this.userservice.getAllInboxText(Number(this.userid));
    this.users  = this.userservice.getAllUserButThis(Number(this.userid));
  }


  onSubmit(){
    if((this.form.inbox==undefined || this.form.inbox=='') || (this.form.sendto==0 || this.form.sendto==undefined)){
      alert(" Please Select User or write text !! ");
      return   
    }
    
    this.form.username = this.userDisplay;
    this.form.fromid = Number(this.userid);
    this.authService.inserText(this.form).subscribe(
      data =>{
        this.form.inbox = '';
        this.form.sendto= 0;
        this.router.navigate(['InboxComponent']);
        this.reloadData();
      },
      err =>{
        this.errorMessage = err.error.message;
      }
    );
  }
  
}
