import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { HomeComponent} from './components/home/home.component';
import { ProfileComponent} from './components/profile/profile.component';
import { ProfileEditComponent } from './components/profile-edit/profile-edit.component';
import { RegSuccessPopupComponent } from './components/reg-success-popup/reg-success-popup.component';
import { VerificationFailedComponent } from './components/verification-failed/verification-failed.component';
import { VideoUploadComponent } from './components/video-upload/video-upload.component';
import { InboxComponent } from './components/inbox/inbox.component';

const routes: Routes = [
  // { path: '', redirectTo: 'home', pathMatch: 'full'},
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'profileEdit', component: ProfileEditComponent},
  { path: 'regSuccessCompo', component: RegSuccessPopupComponent},
  { path: 'VerificationFailedComponent', component:VerificationFailedComponent},
  { path: 'videoLibrary', component:VideoUploadComponent},
  { path: 'InboxComponent', component:InboxComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
