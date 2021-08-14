import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
const AUTH_API = 'http://localhost:8080/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  token: any = {};
  signUpData: any = {};
  user: any = {};

  constructor(private http: HttpClient) { }

  login(credentials): Observable<any> {
    console.log(" auth service "+credentials.username);
    return this.http.post(AUTH_API + 'signin', {
      username: credentials.username,
      password: credentials.password
    }, httpOptions);
  }
 


  register(signUpData): Observable<any>{
    const parsedUrl= new URL(window.location.href);
    return this.http.post(AUTH_API + 'signup',{
      firstName: signUpData.firstName,
      middleName: signUpData.middleName,
      lastName: signUpData.lastName,
      phone: signUpData.phone,
      username: signUpData.username,
      email: signUpData.email,
      password: signUpData.password,
      addressLine1: signUpData.addressLine1,
      addressLine2: signUpData.addressLine2,
      stateName: signUpData.stateName,
      cityName: signUpData.cityName,
      zipCode: signUpData.zipCode,
      isReadTermsAndCondition: signUpData.isReadTermsAndCondition,
      baseFrontURL: parsedUrl.origin
    }, httpOptions);
  }



  inserText(msgData): Observable<any> {
    return this.http.post(AUTH_API + 'saveText', {
      username: msgData.username,
      inbox: msgData.inbox,
      fromid: msgData.fromid,
      sendto: msgData.sendto
    }, httpOptions);
  }


  

  uploadImg(msgData): Observable<any> {
    console.log("msgData.userid: "+msgData.userid);
    console.log("msgData.userimage: "+msgData.userimage);
    return this.http.put(AUTH_API + 'upload', {
      userid: msgData.userid,
      userimage: msgData.userimage
    }, httpOptions);
  }






  onUpload(id: number, userimage: File){
    console.log("ID : "+id);
    console.log("user image : "+userimage);
    //return this.http.post(AUTH_API + 'upload/'+`${id}`, FormData, { observe: 'response' });
  }

  
}
