import { element, by, ElementFinder } from 'protractor';

export class ClienteComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-cliente div table .btn-danger'));
    title = element.all(by.css('jhi-cliente div h2#page-heading span')).first();

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

export class ClienteUpdatePage {
    pageTitle = element(by.id('jhi-cliente-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nombreInput = element(by.id('field_nombre'));
    codigoInput = element(by.id('field_codigo'));
    direccionInput = element(by.id('field_direccion'));
    correoInput = element(by.id('field_correo'));
    telefonoInput = element(by.id('field_telefono'));
    fechaDeNacimientoInput = element(by.id('field_fechaDeNacimiento'));
    sexoSelect = element(by.id('field_sexo'));
    estatusCivilSelect = element(by.id('field_estatusCivil'));
    imagenInput = element(by.id('file_imagen'));
    tipoDeClienteSelect = element(by.id('field_tipoDeCliente'));
    usuarioSelect = element(by.id('field_usuario'));
    tipoDeDocumentoSelect = element(by.id('field_tipoDeDocumento'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNombreInput(nombre) {
        await this.nombreInput.sendKeys(nombre);
    }

    async getNombreInput() {
        return this.nombreInput.getAttribute('value');
    }

    async setCodigoInput(codigo) {
        await this.codigoInput.sendKeys(codigo);
    }

    async getCodigoInput() {
        return this.codigoInput.getAttribute('value');
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

    async setFechaDeNacimientoInput(fechaDeNacimiento) {
        await this.fechaDeNacimientoInput.sendKeys(fechaDeNacimiento);
    }

    async getFechaDeNacimientoInput() {
        return this.fechaDeNacimientoInput.getAttribute('value');
    }

    async setSexoSelect(sexo) {
        await this.sexoSelect.sendKeys(sexo);
    }

    async getSexoSelect() {
        return this.sexoSelect.element(by.css('option:checked')).getText();
    }

    async sexoSelectLastOption() {
        await this.sexoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setEstatusCivilSelect(estatusCivil) {
        await this.estatusCivilSelect.sendKeys(estatusCivil);
    }

    async getEstatusCivilSelect() {
        return this.estatusCivilSelect.element(by.css('option:checked')).getText();
    }

    async estatusCivilSelectLastOption() {
        await this.estatusCivilSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setImagenInput(imagen) {
        await this.imagenInput.sendKeys(imagen);
    }

    async getImagenInput() {
        return this.imagenInput.getAttribute('value');
    }

    async setTipoDeClienteSelect(tipoDeCliente) {
        await this.tipoDeClienteSelect.sendKeys(tipoDeCliente);
    }

    async getTipoDeClienteSelect() {
        return this.tipoDeClienteSelect.element(by.css('option:checked')).getText();
    }

    async tipoDeClienteSelectLastOption() {
        await this.tipoDeClienteSelect
            .all(by.tagName('option'))
            .last()
            .click();
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

    async tipoDeDocumentoSelectLastOption() {
        await this.tipoDeDocumentoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async tipoDeDocumentoSelectOption(option) {
        await this.tipoDeDocumentoSelect.sendKeys(option);
    }

    getTipoDeDocumentoSelect(): ElementFinder {
        return this.tipoDeDocumentoSelect;
    }

    async getTipoDeDocumentoSelectedOption() {
        return this.tipoDeDocumentoSelect.element(by.css('option:checked')).getText();
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

export class ClienteDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-cliente-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-cliente'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
