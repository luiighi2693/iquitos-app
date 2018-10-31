/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CreditoComponentsPage, CreditoDeleteDialog, CreditoUpdatePage } from './credito.page-object';

const expect = chai.expect;

describe('Credito e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let creditoUpdatePage: CreditoUpdatePage;
    let creditoComponentsPage: CreditoComponentsPage;
    let creditoDeleteDialog: CreditoDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Creditos', async () => {
        await navBarPage.goToEntity('credito');
        creditoComponentsPage = new CreditoComponentsPage();
        expect(await creditoComponentsPage.getTitle()).to.eq('Creditos');
    });

    it('should load create Credito page', async () => {
        await creditoComponentsPage.clickOnCreateButton();
        creditoUpdatePage = new CreditoUpdatePage();
        expect(await creditoUpdatePage.getPageTitle()).to.eq('Create or edit a Credito');
        await creditoUpdatePage.cancel();
    });

    it('should create and save Creditos', async () => {
        const nbButtonsBeforeCreate = await creditoComponentsPage.countDeleteButtons();

        await creditoComponentsPage.clickOnCreateButton();
        await promise.all([
            creditoUpdatePage.setMontoInput('5'),
            creditoUpdatePage.setFechaInput('2000-12-31'),
            creditoUpdatePage.setModoDePagoInput('5'),
            creditoUpdatePage.setNumeroInput('5'),
            creditoUpdatePage.setMontoTotalInput('5'),
            creditoUpdatePage.setFechaLimiteInput('2000-12-31'),
            creditoUpdatePage.setNotaDeCreditoInput('notaDeCredito'),
            creditoUpdatePage.ventaSelectLastOption(),
            creditoUpdatePage.compraSelectLastOption()
        ]);
        expect(await creditoUpdatePage.getMontoInput()).to.eq('5');
        expect(await creditoUpdatePage.getFechaInput()).to.eq('2000-12-31');
        expect(await creditoUpdatePage.getModoDePagoInput()).to.eq('5');
        expect(await creditoUpdatePage.getNumeroInput()).to.eq('5');
        expect(await creditoUpdatePage.getMontoTotalInput()).to.eq('5');
        expect(await creditoUpdatePage.getFechaLimiteInput()).to.eq('2000-12-31');
        expect(await creditoUpdatePage.getNotaDeCreditoInput()).to.eq('notaDeCredito');
        await creditoUpdatePage.save();
        expect(await creditoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await creditoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Credito', async () => {
        const nbButtonsBeforeDelete = await creditoComponentsPage.countDeleteButtons();
        await creditoComponentsPage.clickOnLastDeleteButton();

        creditoDeleteDialog = new CreditoDeleteDialog();
        expect(await creditoDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Credito?');
        await creditoDeleteDialog.clickOnConfirmButton();

        expect(await creditoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
