import { element, by, ElementFinder } from 'protractor';

export class SellHasProductComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-sell-has-product div table .btn-danger'));
    title = element.all(by.css('jhi-sell-has-product div h2#page-heading span')).first();

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

export class SellHasProductUpdatePage {
    pageTitle = element(by.id('jhi-sell-has-product-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    quantityInput = element(by.id('field_quantity'));
    discountInput = element(by.id('field_discount'));
    sellSelect = element(by.id('field_sell'));
    variantSelect = element(by.id('field_variant'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setQuantityInput(quantity) {
        await this.quantityInput.sendKeys(quantity);
    }

    async getQuantityInput() {
        return this.quantityInput.getAttribute('value');
    }

    async setDiscountInput(discount) {
        await this.discountInput.sendKeys(discount);
    }

    async getDiscountInput() {
        return this.discountInput.getAttribute('value');
    }

    async sellSelectLastOption() {
        await this.sellSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async sellSelectOption(option) {
        await this.sellSelect.sendKeys(option);
    }

    getSellSelect(): ElementFinder {
        return this.sellSelect;
    }

    async getSellSelectedOption() {
        return this.sellSelect.element(by.css('option:checked')).getText();
    }

    async variantSelectLastOption() {
        await this.variantSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async variantSelectOption(option) {
        await this.variantSelect.sendKeys(option);
    }

    getVariantSelect(): ElementFinder {
        return this.variantSelect;
    }

    async getVariantSelectedOption() {
        return this.variantSelect.element(by.css('option:checked')).getText();
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

export class SellHasProductDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-sellHasProduct-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-sellHasProduct'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
