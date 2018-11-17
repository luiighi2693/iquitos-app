import { element, by, ElementFinder } from 'protractor';

export class CuentaProveedorComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-cuenta-proveedor div table .btn-danger'));
    title = element.all(by.css('jhi-cuenta-proveedor div h2#page-heading span')).first();

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

export class CuentaProveedorUpdatePage {
    pageTitle = element(by.id('jhi-cuenta-proveedor-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    tipoCuentaSelect = element(by.id('field_tipoCuenta'));
    bancoInput = element(by.id('field_banco'));
    nombreCuentaInput = element(by.id('field_nombreCuenta'));
    numeroDeCuentaInput = element(by.id('field_numeroDeCuenta'));
    fechaInput = element(by.id('field_fecha'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setTipoCuentaSelect(tipoCuenta) {
        await this.tipoCuentaSelect.sendKeys(tipoCuenta);
    }

    async getTipoCuentaSelect() {
        return this.tipoCuentaSelect.element(by.css('option:checked')).getText();
    }

    async tipoCuentaSelectLastOption() {
        await this.tipoCuentaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setBancoInput(banco) {
        await this.bancoInput.sendKeys(banco);
    }

    async getBancoInput() {
        return this.bancoInput.getAttribute('value');
    }

    async setNombreCuentaInput(nombreCuenta) {
        await this.nombreCuentaInput.sendKeys(nombreCuenta);
    }

    async getNombreCuentaInput() {
        return this.nombreCuentaInput.getAttribute('value');
    }

    async setNumeroDeCuentaInput(numeroDeCuenta) {
        await this.numeroDeCuentaInput.sendKeys(numeroDeCuenta);
    }

    async getNumeroDeCuentaInput() {
        return this.numeroDeCuentaInput.getAttribute('value');
    }

    async setFechaInput(fecha) {
        await this.fechaInput.sendKeys(fecha);
    }

    async getFechaInput() {
        return this.fechaInput.getAttribute('value');
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

export class CuentaProveedorDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-cuentaProveedor-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-cuentaProveedor'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
