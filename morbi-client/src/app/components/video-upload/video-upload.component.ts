import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../../_services/token-storage.service';
import { UserService } from '../../_services/user.service';
import { UploadService } from '../../_services/upload.service';
import { Video } from './video';
import { Observable } from "rxjs";

@Component({
  selector: 'app-video-upload',
  templateUrl: './video-upload.component.html',
  styleUrls: ['./video-upload.component.css', '../profile/profile.component.css']
})
export class VideoUploadComponent implements OnInit {

  userid: number;
  form: any = {};
  videos: Observable<Video[]>;
  selectedFile: File;
  base64: any;
  retReon: any;
  format: any;
  url;
  userDisplay ='';

  constructor(private router: Router, private userservice: UserService, private tokenStorageService: TokenStorageService, private uploadservice: UploadService) { }

  ngOnInit(): void {
    this.userDisplay = sessionStorage.getItem('loggedUser');
    this.eheckSession(this.userDisplay);
    this.userid = +sessionStorage.getItem('userid');
    this.userImgDisplay(this.userid);
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

  reloadPage(){
    window.location.reload();
  }

  onSubmit(){
    this.uploadVideo();
  }


  uploadVideo(){
    if(this.form.uservideo==undefined){
      alert('Please select file');
      return;
    }
    const fd = new FormData();
    fd.append('uservideo', this.selectedFile);
    this.uploadservice.onVideoUpload(this.userid, fd)
    .subscribe(res =>{
      console.log(res);
      this.reloadPage();
    });
  }


  onFileSelect(event){
    this.selectedFile = event.target.files[0];   
  }


  userImgDisplay(uid){
    this.videos = this.uploadservice.getAllVideo(uid);
  }
}
