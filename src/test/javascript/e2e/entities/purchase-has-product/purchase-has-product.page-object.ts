import { element, by, ElementFinder } from 'protractor';

export class PurchaseHasProductComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-purchase-has-product div table .btn-danger'));
    title = element.all(by.css('jhi-purchase-has-product div h2#page-heading span')).first();

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

export class PurchaseHasProductUpdatePage {
    pageTitle = element(by.id('jhi-purchase-has-product-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    quantityInput = element(by.id('field_quantity'));
    taxInput = element(by.id('field_tax'));
    datePurchaseInput = element(by.id('field_datePurchase'));
    purchaseSelect = element(by.id('field_purchase'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setQuantityInput(quantity) {
        await this.quantityInput.sendKeys(quantity);
    }

    async getQuantityInput() {
        return this.quantityInput.getAttribute('value');
    }

    async setTaxInput(tax) {
        await this.taxInput.sendKeys(tax);
    }

    async getTaxInput() {
        return this.taxInput.getAttribute('value');
    }

    async setDatePurchaseInput(datePurchase) {
        await this.datePurchaseInput.sendKeys(datePurchase);
    }

    async getDatePurchaseInput() {
        return this.datePurchaseInput.getAttribute('value');
    }

    async purchaseSelectLastOption() {
        await this.purchaseSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async purchaseSelectOption(option) {
        await this.purchaseSelect.sendKeys(option);
    }

    getPurchaseSelect(): ElementFinder {
        return this.purchaseSelect;
    }

    async getPurchaseSelectedOption() {
        return this.purchaseSelect.element(by.css('option:checked')).getText();
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

export class PurchaseHasProductDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-purchaseHasProduct-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-purchaseHasProduct'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
