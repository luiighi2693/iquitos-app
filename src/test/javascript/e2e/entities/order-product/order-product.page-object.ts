import { element, by, ElementFinder } from 'protractor';

export class OrderProductComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-order-product div table .btn-danger'));
    title = element.all(by.css('jhi-order-product div h2#page-heading span')).first();

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

export class OrderProductUpdatePage {
    pageTitle = element(by.id('jhi-order-product-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    noteInput = element(by.id('field_note'));
    guideInput = element(by.id('field_guide'));
    orderStatusSelect = element(by.id('field_orderStatus'));
    metaDataInput = element(by.id('field_metaData'));
    providerSelect = element(by.id('field_provider'));
    productsSelect = element(by.id('field_products'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNoteInput(note) {
        await this.noteInput.sendKeys(note);
    }

    async getNoteInput() {
        return this.noteInput.getAttribute('value');
    }

    async setGuideInput(guide) {
        await this.guideInput.sendKeys(guide);
    }

    async getGuideInput() {
        return this.guideInput.getAttribute('value');
    }

    async setOrderStatusSelect(orderStatus) {
        await this.orderStatusSelect.sendKeys(orderStatus);
    }

    async getOrderStatusSelect() {
        return this.orderStatusSelect.element(by.css('option:checked')).getText();
    }

    async orderStatusSelectLastOption() {
        await this.orderStatusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setMetaDataInput(metaData) {
        await this.metaDataInput.sendKeys(metaData);
    }

    async getMetaDataInput() {
        return this.metaDataInput.getAttribute('value');
    }

    async providerSelectLastOption() {
        await this.providerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async providerSelectOption(option) {
        await this.providerSelect.sendKeys(option);
    }

    getProviderSelect(): ElementFinder {
        return this.providerSelect;
    }

    async getProviderSelectedOption() {
        return this.providerSelect.element(by.css('option:checked')).getText();
    }

    async productsSelectLastOption() {
        await this.productsSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async productsSelectOption(option) {
        await this.productsSelect.sendKeys(option);
    }

    getProductsSelect(): ElementFinder {
        return this.productsSelect;
    }

    async getProductsSelectedOption() {
        return this.productsSelect.element(by.css('option:checked')).getText();
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

export class OrderProductDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-orderProduct-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-orderProduct'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
