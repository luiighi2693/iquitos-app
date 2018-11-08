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
    codigoInput = element(by.id('field_codigo'));
    estatusSelect = element(by.id('field_estatus'));
    bancoInput = element(by.id('field_banco'));
    nombreCuentaInput = element(by.id('field_nombreCuenta'));
    numeroDeCuentaInput = element(by.id('field_numeroDeCuenta'));
    fechaInput = element(by.id('field_fecha'));
    proveedorSelect = element(by.id('field_proveedor'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCodigoInput(codigo) {
        await this.codigoInput.sendKeys(codigo);
    }

    async getCodigoInput() {
        return this.codigoInput.getAttribute('value');
    }

    async setEstatusSelect(estatus) {
        await this.estatusSelect.sendKeys(estatus);
    }

    async getEstatusSelect() {
        return this.estatusSelect.element(by.css('option:checked')).getText();
    }

    async estatusSelectLastOption() {
        await this.estatusSelect
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

    async proveedorSelectLastOption() {
        await this.proveedorSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async proveedorSelectOption(option) {
        await this.proveedorSelect.sendKeys(option);
    }

    getProveedorSelect(): ElementFinder {
        return this.proveedorSelect;
    }

    async getProveedorSelectedOption() {
        return this.proveedorSelect.element(by.css('option:checked')).getText();
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
