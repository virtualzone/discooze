import { Component, Input, OnInit } from "@angular/core";
import { Router, ActivatedRoute, Params } from "@angular/router";
import { CrudService } from "../service/crud.service";
import { RestModel } from "../model/rest-model";

export abstract class EntityEditComponent<T extends RestModel<T>> implements OnInit {
    entity: T = this.newTypeInstance();
    submitting: boolean = false;
    success: boolean = false;
    error: boolean = false;
    uuid: string = "";

    constructor(
        protected route: ActivatedRoute,
        protected router: Router,
        protected crudService: CrudService<T>
    ) {}

    protected abstract newTypeInstance(): T;

    protected abstract getListPath(): string;

    protected onInit(): void {
        // Overwrite this in child classes if necessary
    }

    protected onEntityLoaded(): void {
        // Overwrite this in child classes if necessary
    }

    ngOnInit(): void {
        this.route.params.forEach((params: Params) => {
            let uuid: string = params["id"];
            if (uuid) {
                this.uuid = uuid;
            }
        });
        this.onInit();
        if (this.uuid) {
            this.crudService.get(this.uuid).then(entity => {
                this.entity = entity;
                this.onEntityLoaded();
            });
        } else {
            this.entity = this.newTypeInstance();
            this.onEntityLoaded();
        }
    }

    submit(): void {
        this.submitting = true;
        this.success = false;
        this.crudService.save(this.entity)
            .then(entity => {
                this.entity = entity;
                this.submitting = false;
                this.success = true;
            }).catch(e => {
                this.submitting = false;
                this.success = false;
                this.error = true;
            });
    }

    delete(uuid: string): void {
        if (confirm("Delete this record?")) {
            this.submitting = true;
            this.success = false;
            this.crudService.delete(this.entity.id)
                .then(res => this.router.navigate([this.getListPath()]));
        }
    }
}
