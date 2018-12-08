import { element, by, ElementFinder } from 'protractor';

export class UsuarioExternoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-usuario-externo div table .btn-danger'));
    title = element.all(by.css('jhi-usuario-externo div h2#page-heading span')).first();

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

export class UsuarioExternoUpdatePage {
    pageTitle = element(by.id('jhi-usuario-externo-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dniInput = element(by.id('field_dni'));
    pinInput = element(by.id('field_pin'));
    idEntityInput = element(by.id('field_idEntity'));
    userTypeSelect = element(by.id('field_userType'));
    roleInput = element(by.id('field_role'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setDniInput(dni) {
        await this.dniInput.sendKeys(dni);
    }

    async getDniInput() {
        return this.dniInput.getAttribute('value');
    }

    async setPinInput(pin) {
        await this.pinInput.sendKeys(pin);
    }

    async getPinInput() {
        return this.pinInput.getAttribute('value');
    }

    async setIdEntityInput(idEntity) {
        await this.idEntityInput.sendKeys(idEntity);
    }

    async getIdEntityInput() {
        return this.idEntityInput.getAttribute('value');
    }

    async setUserTypeSelect(userType) {
        await this.userTypeSelect.sendKeys(userType);
    }

    async getUserTypeSelect() {
        return this.userTypeSelect.element(by.css('option:checked')).getText();
    }

    async userTypeSelectLastOption() {
        await this.userTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setRoleInput(role) {
        await this.roleInput.sendKeys(role);
    }

    async getRoleInput() {
        return this.roleInput.getAttribute('value');
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

export class UsuarioExternoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-usuarioExterno-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-usuarioExterno'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
