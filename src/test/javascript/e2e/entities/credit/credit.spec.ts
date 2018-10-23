/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CreditComponentsPage, CreditDeleteDialog, CreditUpdatePage } from './credit.page-object';

const expect = chai.expect;

describe('Credit e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let creditUpdatePage: CreditUpdatePage;
    let creditComponentsPage: CreditComponentsPage;
    let creditDeleteDialog: CreditDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Credits', async () => {
        await navBarPage.goToEntity('credit');
        creditComponentsPage = new CreditComponentsPage();
        expect(await creditComponentsPage.getTitle()).to.eq('Credits');
    });

    it('should load create Credit page', async () => {
        await creditComponentsPage.clickOnCreateButton();
        creditUpdatePage = new CreditUpdatePage();
        expect(await creditUpdatePage.getPageTitle()).to.eq('Create or edit a Credit');
        await creditUpdatePage.cancel();
    });

    it('should create and save Credits', async () => {
        const nbButtonsBeforeCreate = await creditComponentsPage.countDeleteButtons();

        await creditComponentsPage.clickOnCreateButton();
        await promise.all([
            creditUpdatePage.setAmountInput('5'),
            creditUpdatePage.setEmissionDateInput('2000-12-31'),
            creditUpdatePage.setPaymentModeInput('5'),
            creditUpdatePage.setCreditNumberInput('5'),
            creditUpdatePage.setTotalAmountInput('5'),
            creditUpdatePage.setLimitDateInput('2000-12-31'),
            creditUpdatePage.setCreditNoteInput('creditNote'),
            creditUpdatePage.sellSelectLastOption(),
            creditUpdatePage.purchaseSelectLastOption()
        ]);
        expect(await creditUpdatePage.getAmountInput()).to.eq('5');
        expect(await creditUpdatePage.getEmissionDateInput()).to.eq('2000-12-31');
        expect(await creditUpdatePage.getPaymentModeInput()).to.eq('5');
        expect(await creditUpdatePage.getCreditNumberInput()).to.eq('5');
        expect(await creditUpdatePage.getTotalAmountInput()).to.eq('5');
        expect(await creditUpdatePage.getLimitDateInput()).to.eq('2000-12-31');
        expect(await creditUpdatePage.getCreditNoteInput()).to.eq('creditNote');
        await creditUpdatePage.save();
        expect(await creditUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await creditComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Credit', async () => {
        const nbButtonsBeforeDelete = await creditComponentsPage.countDeleteButtons();
        await creditComponentsPage.clickOnLastDeleteButton();

        creditDeleteDialog = new CreditDeleteDialog();
        expect(await creditDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Credit?');
        await creditDeleteDialog.clickOnConfirmButton();

        expect(await creditComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
