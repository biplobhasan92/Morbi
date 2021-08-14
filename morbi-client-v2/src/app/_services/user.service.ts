import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../components/user';
import { formatDate } from '@angular/common';

const API_URL = 'http://localhost:8080/api/';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})
export class UserService {

  signUpData: any = {};
  user: any = {};

  constructor(private http: HttpClient) { }


  
  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }




  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }




  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }




  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }




  getUserByUsername(username: string): Observable<any> {
    return this.http.get(API_URL + 'test/userbyname/'+`${username}`, { responseType: 'text' });
  }




  getAllInboxText(userid: number): Observable<any>{ // getallinbox
    return this.http.get(API_URL + 'test/getallinbox/'+`${userid}`);
  }



  getAllUserButThis(userid: number): Observable<any>{
    return this.http.get(API_URL + 'test/getUserComboData/'+`${userid}`);
  }




  updateUser(signUpData): Observable<any> {
    return this.http.post(API_URL + 'user', {
      id: signUpData.id,
      firstName: signUpData.firstName,
      lastName: signUpData.lastName,
      phone: signUpData.phone,
      email: signUpData.email
    }, httpOptions);
  }
  


  


  updateEmp(msgData): Observable<any>{
    return this.http.put(API_URL + 'test/updateUser',{
      userid: msgData.userid,
      firstName : msgData.firstName,
      lastName : msgData.lastName,
      email : msgData.email,
      phone : msgData.phone
    }, httpOptions);
  }



}
