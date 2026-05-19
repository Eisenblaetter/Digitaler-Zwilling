/* (c) https://github.com/MontiCore/monticore */
import { Route } from "@angular/router";
import { routes as routesTOP } from "AppGemRoutingTOP";

export const routes: Route[] = [
  {
    path: "",
    pathMatch: "full",
    redirectTo: "gui/Start"
  },
  /*SOL start 03_TOPMechanism.md*/
  {
    path: "",
    pathMatch: "full",
    redirectTo: "gui/SimpleCarList"
  },
  /*SOL end 03_TOPMechanism.md*/
  ...routesTOP
]
