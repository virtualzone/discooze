import { Injectable } from "@angular/core";
import { Headers, Http, Response } from "@angular/http";

import { SessionService } from "./session.service";

@Injectable()
export class HttpService {
    restUrl: string;

    constructor(
        private sessionService: SessionService
    ) {
        let baseHref = document.getElementsByTagName("base")[0].getAttribute("href");
        if (location.port === "3001") {
            this.restUrl = "http://" + location.hostname + ":8080" + baseHref + "api/";
        } else {
            this.restUrl = baseHref + "api/";
        }
    }

    getHeaders(): Headers {
        return new Headers({
            "Content-Type": "application/json",
            "Authorization": "Bearer " + this.sessionService.jwt
        });
    }

    getOptions(): any {
        return {headers: this.getHeaders()};
    }

    getUrl(service: string): string {
        return this.restUrl + service;
    }

    handleError(error: Response): Response {
        if (error.status === 403) {
            this.sessionService.logout();
            return;
        }
        return error;
    }
}
