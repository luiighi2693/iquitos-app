import { element, by, ElementFinder } from 'protractor';

export class ProveedorComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-proveedor div table .btn-danger'));
    title = element.all(by.css('jhi-proveedor div h2#page-heading span')).first();

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

export class ProveedorUpdatePage {
    pageTitle = element(by.id('jhi-proveedor-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codigoInput = element(by.id('field_codigo'));
    razonSocialInput = element(by.id('field_razonSocial'));
    direccionInput = element(by.id('field_direccion'));
    correoInput = element(by.id('field_correo'));
    telefonoInput = element(by.id('field_telefono'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCodigoInput(codigo) {
        await this.codigoInput.sendKeys(codigo);
    }

    async getCodigoInput() {
        return this.codigoInput.getAttribute('value');
    }

    async setRazonSocialInput(razonSocial) {
        await this.razonSocialInput.sendKeys(razonSocial);
    }

    async getRazonSocialInput() {
        return this.razonSocialInput.getAttribute('value');
    }

    async setDireccionInput(direccion) {
        await this.direccionInput.sendKeys(direccion);
    }

    async getDireccionInput() {
        return this.direccionInput.getAttribute('value');
    }

    async setCorreoInput(correo) {
        await this.correoInput.sendKeys(correo);
    }

    async getCorreoInput() {
        return this.correoInput.getAttribute('value');
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

export class ProveedorDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-proveedor-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-proveedor'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
