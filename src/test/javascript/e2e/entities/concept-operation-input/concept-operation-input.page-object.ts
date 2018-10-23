import { element, by, ElementFinder } from 'protractor';

export class ConceptOperationInputComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-concept-operation-input div table .btn-danger'));
    title = element.all(by.css('jhi-concept-operation-input div h2#page-heading span')).first();

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

export class ConceptOperationInputUpdatePage {
    pageTitle = element(by.id('jhi-concept-operation-input-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    valueInput = element(by.id('field_value'));
    metaDataInput = element(by.id('field_metaData'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setValueInput(value) {
        await this.valueInput.sendKeys(value);
    }

    async getValueInput() {
        return this.valueInput.getAttribute('value');
    }

    async setMetaDataInput(metaData) {
        await this.metaDataInput.sendKeys(metaData);
    }

    async getMetaDataInput() {
        return this.metaDataInput.getAttribute('value');
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

export class ConceptOperationInputDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-conceptOperationInput-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-conceptOperationInput'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
