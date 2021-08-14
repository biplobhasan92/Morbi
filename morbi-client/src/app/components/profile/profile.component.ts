import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../_services/token-storage.service';
import { Router } from '@angular/router';
import { UserService } from '../../_services/user.service';
import { UploadService } from '../../_services/upload.service';
import { User } from '../user';
import { ProfileEditComponent } from '../profile-edit/profile-edit.component';



@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})


export class ProfileComponent implements OnInit {


  uid : number;
  fstName = '';
  lstName='';
  retrievedImage: any;
  base64: any;
  retReon: any;

  constructor(private tokenStorageService: TokenStorageService, private router: Router, private userservice: UserService, private uploadservice: UploadService) { }
  userDisplayName = '';
  


  ngOnInit(): void{
    this.userDisplayName = sessionStorage.getItem('loggedUser');
    this.eheckSession(this.userDisplayName);
    this.userservice.getUserByUsername(this.userDisplayName).subscribe(data =>{
      var JSONObject = JSON.parse(data);
      this.uid = JSONObject.id;
      sessionStorage.setItem('userid', this.uid.toString());
      this.fstName = JSONObject.firstName;
      this.lstName = JSONObject.lastName;
      this.userImgDisplay(this.uid); // this should call in edit-profile
    }, error => console.log(error));    
    console.log("console #### ");
    ; 
  }


  eheckSession(sessionName){
    if(sessionName=='' || sessionName==null){
      this.router.navigate(['login']); 
    }else{
      
    }
  }

  userImgDisplay(uid){
    this.uploadservice.getImage(uid).subscribe( res =>{
      this.retReon = res;
      this.base64 = this.retReon.picByte;
      this.url = 'data:image/jpeg;base64,' + this.base64;
    });
  }
  


  url: string | ArrayBuffer =
  "https://bulma.io/images/placeholders/480x480.png";


  logout(){
    this.tokenStorageService.signOut();
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
