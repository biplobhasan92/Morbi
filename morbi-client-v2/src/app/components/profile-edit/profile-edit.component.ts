import { formatDate } from '@angular/common';
import { ReadVarExpr } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { TokenStorageService } from '../../_services/token-storage.service';
import { UserService } from '../../_services/user.service';
import { AuthService } from '../../_services/auth.service';
import { UploadService } from '../../_services/upload.service';
import { User } from '../user';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';


@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.css', '../profile/profile.component.css']
})


export class ProfileEditComponent implements OnInit{
  
  changeText1: boolean;
  changeText2: boolean;
  changeText3: boolean;
  changeText4: boolean;

  selectedFile: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;

  form: any = {};
  firstName = '';
  fstName ='';
  lastName = '';
  email = '';
  mobile = '';
  errorMessage = '';
  id: number;
  username: string;
  user: User = new User();
  constructor(
      private route: ActivatedRoute, 
      private router: Router, 
      private userservice: UserService, 
      private authservice : AuthService, 
      private sanitizer:DomSanitizer, 
      private uploadservice: UploadService,
      private tokenStorageService: TokenStorageService,
  ) {
    this.changeText1 = false;
    this.changeText2 = false;
    this.changeText3 = false;
    this.changeText4 = false;
   }

  ngOnInit(): void {
    this.form = this.userservice.signUpData;
    this.id   = this.route.snapshot.params['id'];
    this.firstName= this.route.snapshot.params['firstName'];  
    this.username = sessionStorage.getItem('loggedUser');
    this.fstName = sessionStorage.getItem('fstName');
    this.eheckSession(this.username);
    this.userservice.getUserByUsername(this.username)
    .subscribe(data =>{
      var JSONObject = JSON.parse(data);
      this.user = data;
      this.form.userid = JSONObject.id;
      this.form.firstName = JSONObject.firstName;
      this.form.lastName = JSONObject.lastName;
      this.form.email = JSONObject.email;
      this.form.phone = JSONObject.phone;
      this.displayImage(this.form.userid);
    }, error => console.log(error));
  }


  get SumValue(){
    return this.url;
  }

  eheckSession(sessionName){
    if(sessionName=='' || sessionName==null){
      this.router.navigate(['login']); 
    }else{

    }
  }

  onSubmit(){
    this.updateEmployee();
  }



  updateProfilePic(){
    const fd = new FormData();
    fd.append('userimage', this.selectedFile);
    this.uploadservice.onUpload(this.form.userid ,fd)
    .subscribe(res =>{
      console.log(res);      
    });
  }




  displayImage(userid){
    this.uploadservice.getImage(userid).subscribe( res =>{
      this.retrieveResonse = res;
      this.base64Data = this.retrieveResonse.picByte;
      this.url = 'data:image/jpeg;base64,' + this.base64Data;
    });
  }





  updateEmployee(){
    this.updateProfilePic();
    this.userservice.updateEmp(this.form).subscribe(
      data =>{
        this.router.navigate(['profile'])
          .then(() => {
            window.location.reload();
          });
      },
      err =>{
        this.errorMessage = err.error.message;
      }
    );
  }





  onFileSelect(event){
      if((event.target.files[0].size*0.0009765625)>201){
        alert('Please select file size less than 200 kb');
        this.form.userimage = '';
        return;
      }
      this.selectedFile = <File>event.target.files[0];
      this.showThum(this.selectedFile); 
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




  gotoProfileEdit(){
    this.router.navigate(['profileEdit']);
  }


  gotoSettings(){
    this.router.navigate(['Settings']);
  }

  gotoProfile(){
    this.router.navigate(['profile']);
  }



  logout() {
    this.tokenStorageService.signOut();
    this.router.navigate(['login']);
    sessionStorage.clear();
  }


  gotoVideo(){
    this.router.navigate(['videoLibrary']);
  }


  gotoIndex(){
    this.router.navigate(['InboxComponent']);
  }

}
