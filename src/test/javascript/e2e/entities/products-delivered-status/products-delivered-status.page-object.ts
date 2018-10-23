import { element, by, ElementFinder } from 'protractor';

export class ProductsDeliveredStatusComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-products-delivered-status div table .btn-danger'));
    title = element.all(by.css('jhi-products-delivered-status div h2#page-heading span')).first();

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

export class ProductsDeliveredStatusUpdatePage {
    pageTitle = element(by.id('jhi-products-delivered-status-heading'));
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

export class ProductsDeliveredStatusDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productsDeliveredStatus-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productsDeliveredStatus'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
