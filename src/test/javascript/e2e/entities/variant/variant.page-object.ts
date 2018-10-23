import { element, by, ElementFinder } from 'protractor';

export class VariantComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-variant div table .btn-danger'));
    title = element.all(by.css('jhi-variant div h2#page-heading span')).first();

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

export class VariantUpdatePage {
    pageTitle = element(by.id('jhi-variant-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    descriptionInput = element(by.id('field_description'));
    priceSellInput = element(by.id('field_priceSell'));
    privePurchaseInput = element(by.id('field_privePurchase'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setPriceSellInput(priceSell) {
        await this.priceSellInput.sendKeys(priceSell);
    }

    async getPriceSellInput() {
        return this.priceSellInput.getAttribute('value');
    }

    async setPrivePurchaseInput(privePurchase) {
        await this.privePurchaseInput.sendKeys(privePurchase);
    }

    async getPrivePurchaseInput() {
        return this.privePurchaseInput.getAttribute('value');
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

export class VariantDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-variant-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-variant'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
