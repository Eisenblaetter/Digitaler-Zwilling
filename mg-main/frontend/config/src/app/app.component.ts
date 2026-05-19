import { Component } from '@angular/core';
import { DigitalTwinManagerClientImpl } from 'DigitalTwin/DigitalTwinManagerClientImpl';
import { DigitalTwinComponentObserverManager } from 'DigitalTwinComponentObserverManager';
import {GemLineChartTypesDeSer} from '@umlp/charts'
import { ClientCommandManager,BaseClassObjectMapper , ClientCommandWebsocketCommunication, CommandManager } from '@umlp/commonj2ts';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor() {
    DigitalTwinManagerClientImpl.init();
    const cmdManager = new ClientCommandManager(new ClientCommandWebsocketCommunication(
      `${document.baseURI.replace(/^http/, 'ws').replace(/\/$/, '')}` + '/umlp/api/command'
    ));
    CommandManager.initMe(cmdManager);
    (<any>window).websocket_connected = true;
    DigitalTwinComponentObserverManager.init();
    BaseClassObjectMapper.addDeSers(GemLineChartTypesDeSer.getInstance().getDeSerMap())
  }
}
