import {ModuleWithProviders, NgModule} from "@angular/core";
import {HttpModule} from "@angular/http";

import {ErrorModule} from "../error/error.module";
import {RestClientService} from "./services/rest-client.service";
import {HttpHelper} from "./helper/http.helper";

@NgModule({
  imports: [HttpModule, ErrorModule]
})
export class RestModule {
 public static forRoot(): ModuleWithProviders {
   return {
    ngModule: RestModule,
    providers: [RestClientService, HttpHelper]
   };
  }
}
