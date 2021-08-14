import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { RegistrationComponent } from './components/registration/registration.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { VerificationFailedComponent } from './components/verification-failed/verification-failed.component';
import { ProfileEditComponent } from './components/profile-edit/profile-edit.component';
import { VideoUploadComponent } from './components/video-upload/video-upload.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { MenuComponent } from '../app/components/menu/menu.component';
import { FooterComponent } from '../app/components/footer/footer.component';
import { SecureMenuComponent } from './components/secure-menu/secure-menu.component';
import { SettingsComponent } from './components/settings/settings.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    HomeComponent,
    ProfileComponent,
    VerificationFailedComponent,
    ProfileEditComponent,
    VideoUploadComponent,
    InboxComponent,
    MenuComponent,
    FooterComponent,
    SecureMenuComponent,
    SettingsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
