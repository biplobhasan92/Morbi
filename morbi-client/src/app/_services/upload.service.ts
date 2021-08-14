import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../components/user';
import { formatDate } from '@angular/common';

const API_URL = 'http://localhost:8080/api/';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  
  constructor(private http: HttpClient) { }



  onUpload(userid: number, FormData){
    return this.http.post(API_URL + 'image/uploadFile/'+`${userid}`, FormData, { observe: 'response' });
  }



  getImage(userid: number){
    return this.http.get(API_URL + 'image/userImage/'+`${userid}`);
  }



  onVideoUpload(userid: number, FormData){
    return this.http.post(API_URL + 'video/uploadFile/'+`${userid}`, FormData, { observe: 'response' });
  }




  getAllVideo(userid: number): Observable<any>{
    return this.http.get(API_URL + 'video/getAllVideoFile/'+`${userid}`);
  }


}
