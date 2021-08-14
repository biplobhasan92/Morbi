import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../_services/token-storage.service';
import { Router } from '@angular/router';
import { Observable } from "rxjs";
import { UserService } from '../../_services/user.service';
import { AuthService } from 'src/app/_services/auth.service';
import { Inbox } from './inbox';
import { UserCombo } from './usercombo';
import { User } from '../user';
import { UploadService } from '../../_services/upload.service';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css', '../profile/profile.component.css']
})


export class InboxComponent implements OnInit{

  changeText1: boolean;
  changeText2: boolean;
  changeText3: boolean;
  changeText4: boolean;

  form: any = {};
  inboxs: Observable<Inbox[]>;
  users: Observable<User[]>;
  errorMessage='';

  fstName = '';
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;

  constructor(private authService: AuthService, private tokenStorageService: TokenStorageService, private router: Router,private userservice: UserService,private uploadservice: UploadService) {
    this.changeText1 = false;
    this.changeText2 = false;
    this.changeText3 = false;
    this.changeText4 = false;
   }
  userDisplay ='';
  userid : any;

  ngOnInit(): void {
    this.userDisplay = sessionStorage.getItem('loggedUser');
    this.eheckSession(this.userDisplay);
    this.userid = sessionStorage.getItem('userid'); //work from here ... 
    this.fstName= sessionStorage.getItem('fstName');
    this.reloadData();
    this.displayImage(this.userid);
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



  url: string | ArrayBuffer =
  "https://bulma.io/images/placeholders/480x480.png";
  showThum(fileInput){
      var reader = new FileReader();
      reader.readAsDataURL(fileInput);
      reader.onload=(event:any)=>{
        this.url = event.target.result;
      }
  }



  displayImage(userid){
    this.uploadservice.getImage(userid).subscribe( res =>{
      this.retrieveResonse = res;
      this.base64Data = this.retrieveResonse.picByte;
      this.url = 'data:image/jpeg;base64,' + this.base64Data;
    });
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

  gotoSettings(){
    this.router.navigate(['Settings']);
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
