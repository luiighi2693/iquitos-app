import { element, by, ElementFinder } from 'protractor';

export class EmpleadoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-empleado div table .btn-danger'));
    title = element.all(by.css('jhi-empleado div h2#page-heading span')).first();

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

export class EmpleadoUpdatePage {
    pageTitle = element(by.id('jhi-empleado-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nombreInput = element(by.id('field_nombre'));
    apellidoInput = element(by.id('field_apellido'));
    dniInput = element(by.id('field_dni'));
    direccionInput = element(by.id('field_direccion'));
    correoInput = element(by.id('field_correo'));
    fechaDeNacimientoInput = element(by.id('field_fechaDeNacimiento'));
    sexoSelect = element(by.id('field_sexo'));
    telefonoInput = element(by.id('field_telefono'));
    imagenInput = element(by.id('file_imagen'));
    rolEmpleadoSelect = element(by.id('field_rolEmpleado'));
    usuarioSelect = element(by.id('field_usuario'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNombreInput(nombre) {
        await this.nombreInput.sendKeys(nombre);
    }

    async getNombreInput() {
        return this.nombreInput.getAttribute('value');
    }

    async setApellidoInput(apellido) {
        await this.apellidoInput.sendKeys(apellido);
    }

    async getApellidoInput() {
        return this.apellidoInput.getAttribute('value');
    }

    async setDniInput(dni) {
        await this.dniInput.sendKeys(dni);
    }

    async getDniInput() {
        return this.dniInput.getAttribute('value');
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

    async setTelefonoInput(telefono) {
        await this.telefonoInput.sendKeys(telefono);
    }

    async getTelefonoInput() {
        return this.telefonoInput.getAttribute('value');
    }

    async setImagenInput(imagen) {
        await this.imagenInput.sendKeys(imagen);
    }

    async getImagenInput() {
        return this.imagenInput.getAttribute('value');
    }

    async setRolEmpleadoSelect(rolEmpleado) {
        await this.rolEmpleadoSelect.sendKeys(rolEmpleado);
    }

    async getRolEmpleadoSelect() {
        return this.rolEmpleadoSelect.element(by.css('option:checked')).getText();
    }

    async rolEmpleadoSelectLastOption() {
        await this.rolEmpleadoSelect
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

export class EmpleadoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-empleado-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-empleado'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
