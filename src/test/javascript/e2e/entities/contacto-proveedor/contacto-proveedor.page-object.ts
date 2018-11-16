import { element, by, ElementFinder } from 'protractor';

export class ContactoProveedorComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-contacto-proveedor div table .btn-danger'));
    title = element.all(by.css('jhi-contacto-proveedor div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class ContactoProveedorUpdatePage {
    pageTitle = element(by.id('jhi-contacto-proveedor-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nombreInput = element(by.id('field_nombre'));
    cargoInput = element(by.id('field_cargo'));
    productoInput = element(by.id('field_producto'));
    telefonoInput = element(by.id('field_telefono'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNombreInput(nombre) {
        await this.nombreInput.sendKeys(nombre);
    }

    async getNombreInput() {
        return this.nombreInput.getAttribute('value');
    }

    async setCargoInput(cargo) {
        await this.cargoInput.sendKeys(cargo);
    }

    async getCargoInput() {
        return this.cargoInput.getAttribute('value');
    }

    async setProductoInput(producto) {
        await this.productoInput.sendKeys(producto);
    }

    async getProductoInput() {
        return this.productoInput.getAttribute('value');
    }

    async setTelefonoInput(telefono) {
        await this.telefonoInput.sendKeys(telefono);
    }

    async getTelefonoInput() {
        return this.telefonoInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class ContactoProveedorDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-contactoProveedor-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-contactoProveedor'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
