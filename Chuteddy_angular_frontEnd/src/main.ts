import { bootstrapApplication } from '@angular/platform-browser';
import { HomeComponent } from './app/home/home.component';
import { DetailProductComponent } from './app/detail-product/detail-product.component';
import { OrderComponent } from './app/order/order.component';
import { OrderConfirmComponent } from './app/order-confirm/order-confirm.component';
import { LoginComponent } from './app/login/login.component';
import { RegisterComponent } from './app/register/register.component';
import { AllProductComponent } from './app/all-product/all-product.component';

import { config } from './app/app.config.server';


// const bootstrap = () => bootstrapApplication(HomeComponent, config);
// const bootstrap = () => bootstrapApplication(DetailProductComponent, config);
// const bootstrap = () => bootstrapApplication(OrderComponent, config);
// const bootstrap = () => bootstrapApplication(OrderConfirmComponent, config);
// const bootstrap =() =>bootstrapApplication(LoginComponent, config);
bootstrapApplication(RegisterComponent).catch((err) => console.error(err));
// const bootstrap = () => bootstrapApplication(AllProductComponent, config);



