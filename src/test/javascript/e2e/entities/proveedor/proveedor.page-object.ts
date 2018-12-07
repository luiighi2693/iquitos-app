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
    sectorInput = element(by.id('field_sector'));
    usuarioSelect = element(by.id('field_usuario'));
    cuentaProveedorSelect = element(by.id('field_cuentaProveedor'));
    contactoProveedorSelect = element(by.id('field_contactoProveedor'));

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

    async setSectorInput(sector) {
        await this.sectorInput.sendKeys(sector);
    }

    async getSectorInput() {
        return this.sectorInput.getAttribute('value');
    }

    async usuarioSelectLastOption() {
        await this.usuarioSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async usuarioSelectOption(option) {
        await this.usuarioSelect.sendKeys(option);
    }

    getUsuarioSelect(): ElementFinder {
        return this.usuarioSelect;
    }

    async getUsuarioSelectedOption() {
        return this.usuarioSelect.element(by.css('option:checked')).getText();
    }

    async cuentaProveedorSelectLastOption() {
        await this.cuentaProveedorSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async cuentaProveedorSelectOption(option) {
        await this.cuentaProveedorSelect.sendKeys(option);
    }

    getCuentaProveedorSelect(): ElementFinder {
        return this.cuentaProveedorSelect;
    }

    async getCuentaProveedorSelectedOption() {
        return this.cuentaProveedorSelect.element(by.css('option:checked')).getText();
    }

    async contactoProveedorSelectLastOption() {
        await this.contactoProveedorSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async contactoProveedorSelectOption(option) {
        await this.contactoProveedorSelect.sendKeys(option);
    }

    getContactoProveedorSelect(): ElementFinder {
        return this.contactoProveedorSelect;
    }

    async getContactoProveedorSelectedOption() {
        return this.contactoProveedorSelect.element(by.css('option:checked')).getText();
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
