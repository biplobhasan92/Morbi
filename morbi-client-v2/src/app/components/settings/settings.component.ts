import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UploadService } from '../../_services/upload.service';


@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css','../profile/profile.component.css']
})
export class SettingsComponent implements OnInit {
  
  changeText1: boolean;
  changeText2: boolean;
  changeText3: boolean;
  changeText4: boolean;
  fstName ='';
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;

  userid: any;
  constructor(private router: Router, private uploadservice: UploadService) {
    this.changeText1 = false;
    this.changeText2 = false;
    this.changeText3 = false;
    this.changeText4 = false;
  }

  ngOnInit(): void {
    this.fstName= sessionStorage.getItem('fstName');
    this.userid = sessionStorage.getItem('userid');
    this.displayImage(this.userid);
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


  url: string | ArrayBuffer =
  "https://bulma.io/images/placeholders/480x480.png";
  showThum(fileInput){
      var reader = new FileReader();
      reader.readAsDataURL(fileInput);
      reader.onload=(event:any)=>{
        this.url = event.target.result;
      }
  }
}
