import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-all-product',
  imports: [HeaderComponent, FooterComponent], 
  templateUrl: './all-product.component.html',
  styleUrl: './all-product.component.scss'
})
export class AllProductComponent {

}
